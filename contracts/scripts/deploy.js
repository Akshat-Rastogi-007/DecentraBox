const hre = require("hardhat");

async function main() {
  // Get deployer account
  const [deployer] = await hre.ethers.getSigners();

  console.log("🚀 Deploying contract with account:", deployer.address);

  const balance = await hre.ethers.provider.getBalance(deployer.address);
  console.log("💰 Deployer balance:", hre.ethers.formatEther(balance), "ETH");

  // Get contract factory
  const DecentraBoxRegistry = await hre.ethers.getContractFactory("DecentraBoxRegistry");

  // Deploy contract
  const contract = await DecentraBoxRegistry.deploy();

  // Wait for deployment
  await contract.waitForDeployment();

  const contractAddress = await contract.getAddress();

  console.log("✅ DecentraBoxRegistry deployed to:", contractAddress);
}

// Handle errors properly
main().catch((error) => {
  console.error("❌ Deployment failed:", error);
  process.exitCode = 1;
});