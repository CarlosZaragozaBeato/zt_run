// src/utils/getUserToken.ts
const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

/**
 * Retorna el token para un usuario y una marca (ej: POLAR)
 */
async function getUserToken(userId, brandName) {
  const device = await prisma.device.findFirst({
    where: {
      userId,
      brandName,
      brandIdAccount: {
        not: null
      }
    },
    include: {
      brand: {
        include: {
          tokens: {
            orderBy: { createdAt: 'desc' }, // último token si hay varios
            take: 1
          }
        }
      }
    }
  });
  if (!device || !device.brand || device.brand.tokens.length === 0) {
    throw new Error(`No ${brandName} token found for user ${userId}`);
  }

  return device.brand.tokens[0]; // Devuelve el último token disponible
}

module.exports = getUserToken;
