/*
  Warnings:

  - You are about to drop the column `integrationActive` on the `BrandAccount` table. All the data in the column will be lost.

*/
-- CreateTable
CREATE TABLE "BrandAccountStatus" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "integrationActive" BOOLEAN NOT NULL DEFAULT false,
    "brandIdAccount" INTEGER NOT NULL,
    "lastSync" DATETIME NOT NULL,
    CONSTRAINT "BrandAccountStatus_brandIdAccount_fkey" FOREIGN KEY ("brandIdAccount") REFERENCES "BrandAccount" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);

-- RedefineTables
PRAGMA defer_foreign_keys=ON;
PRAGMA foreign_keys=OFF;
CREATE TABLE "new_BrandAccount" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "password" TEXT NOT NULL,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" DATETIME NOT NULL
);
INSERT INTO "new_BrandAccount" ("createdAt", "email", "id", "name", "password", "updatedAt") SELECT "createdAt", "email", "id", "name", "password", "updatedAt" FROM "BrandAccount";
DROP TABLE "BrandAccount";
ALTER TABLE "new_BrandAccount" RENAME TO "BrandAccount";
PRAGMA foreign_keys=ON;
PRAGMA defer_foreign_keys=OFF;
