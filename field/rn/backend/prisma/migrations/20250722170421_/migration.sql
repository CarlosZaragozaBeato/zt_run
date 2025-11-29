/*
  Warnings:

  - You are about to drop the column `brand` on the `Device` table. All the data in the column will be lost.
  - Added the required column `brandId` to the `Device` table without a default value. This is not possible if the table is not empty.

*/
-- CreateTable
CREATE TABLE "BrandAccount" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "name" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "password" TEXT NOT NULL,
    "createdAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updatedAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- RedefineTables
PRAGMA defer_foreign_keys=ON;
PRAGMA foreign_keys=OFF;
CREATE TABLE "new_Device" (
    "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    "userId" INTEGER NOT NULL,
    "type" TEXT NOT NULL,
    "brandId" INTEGER NOT NULL,
    "model" TEXT,
    "serial" TEXT,
    "connectedAt" DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "Device_userId_fkey" FOREIGN KEY ("userId") REFERENCES "User" ("id") ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT "Device_brandId_fkey" FOREIGN KEY ("brandId") REFERENCES "BrandAccount" ("id") ON DELETE RESTRICT ON UPDATE CASCADE
);
INSERT INTO "new_Device" ("connectedAt", "id", "model", "serial", "type", "userId") SELECT "connectedAt", "id", "model", "serial", "type", "userId" FROM "Device";
DROP TABLE "Device";
ALTER TABLE "new_Device" RENAME TO "Device";
CREATE UNIQUE INDEX "Device_serial_key" ON "Device"("serial");
PRAGMA foreign_keys=ON;
PRAGMA defer_foreign_keys=OFF;
