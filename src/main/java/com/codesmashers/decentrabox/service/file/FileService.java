package com.codesmashers.decentrabox.service.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codesmashers.decentrabox.exception.BadRequestException;
import com.codesmashers.decentrabox.exception.ValidationException;
import com.codesmashers.decentrabox.model.FileMetaData;
import com.codesmashers.decentrabox.model.User;
import com.codesmashers.decentrabox.model.dto.response.ApiResponseDto;
import com.codesmashers.decentrabox.repository.FileMetaDataRepository;
import com.codesmashers.decentrabox.security.user.UserDetailsImpl;
import com.codesmashers.decentrabox.service.IPFS.IPFSService;

@Service
public class FileService {

    private final FileMetaDataRepository fDataRepository;
    private final IPFSService ipfsService;

    public FileService(FileMetaDataRepository fDataRepository, IPFSService ipfsService) {
        this.fDataRepository = fDataRepository;
        this.ipfsService = ipfsService;
    }

    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "application/pdf",
            "image/jpeg",
            "image/png",
            "image/gif",
            "text/plain",
            "application/msword",
            "application/vnd.openxmlformats-officedocument" +
                    ".wordprocessingml.document");

    private static final long MAX_FILE_SIZE = 50 * 1024 * 1024;

    public ResponseEntity<ApiResponseDto<?>> uploadFile(MultipartFile file) {

        try {

            validateFile(file);

            UserDetailsImpl userImpl = getCurrectUser();

            if (userImpl == null) {
                throw new BadRequestException("Token Expired, Kindly login again");
            }

            User user = userImpl.getUser();

            // Step 3: calling ipfs
            String cid = ipfsService.uploadFile(file, file.getOriginalFilename());

            // Step 4: Create new FileMetaData
            FileMetaData fileMetadata = new FileMetaData();
            fileMetadata.setId(UUID.randomUUID().toString());
            fileMetadata.setCid(cid);
            fileMetadata.setFileName(file.getOriginalFilename());
            fileMetadata.setMimeType(file.getContentType());
            fileMetadata.setFileSize(file.getSize());
            fileMetadata.setOwnerWallet("wallet_address"); // TODO: Get from blockchain config
            fileMetadata.setUser(user);
            fileMetadata.setTxStatus("PENDING");
            fileMetadata.setTxHash(""); // TODO: blockchainService.registerFile(cid)
            fileMetadata.setPiiDetected(false); // AI will update later
            fileMetadata.setSummary(null); // AI will update later
            fileMetadata.setTags(new ArrayList<>()); // empty list

            // Step 5: Save to DB
            FileMetaData savedMetadata = fDataRepository.save(fileMetadata);

            return buildResponse(savedMetadata, cid, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private UserDetailsImpl getCurrectUser() {

        return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty())
            throw new ValidationException("File is empty or null", "FILE_EMPTY");

        if (file.getSize() > MAX_FILE_SIZE)
            throw new ValidationException("File size exceeds maximum allowed size of 50 MB", "FILE_SIZE_EXCEEDED");

        String mimeType = file.getContentType();

        if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType))
            throw new ValidationException("File type not supported. Allowed types: PDF, JPEG, PNG, GIF, TXT, DOC, DOCX",
                    "UNSUPPORTED_FILE_TYPE");

    }

    private ResponseEntity<ApiResponseDto<?>> buildResponse(Object data, String message, HttpStatus status) {
        return new ResponseEntity<>(new ApiResponseDto<>(Collections.EMPTY_MAP, message, status), status);
    }
}
