const polarAdapter = require("../services/adapters/polar.adapter");

const axios = require("axios");
const qs = require("qs");
const fs = require("fs");
const path = require("path");
const { PrismaClient } = require("@prisma/client");

const POLAR_CLIENT_ID = process.env.POLAR_CLIENT_ID;
const POLAR_CLIENT_SECRET = process.env.POLAR_CLIENT_SECRET;
const POLAR_REDIRECT_URI = process.env.POLAR_REDIRECT_URI;
const POLAR_TOKEN_URL = "https://polarremote.com/v2/oauth2/token";

const prisma = new PrismaClient();


module.exports = async function (fastify) {
  // Exchange code for token
  fastify.post("/test/auth/polar/token", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      const userId = request.user?.id;

        if (!userId){
          return reply.status(401).send({error: "Unauthorized"});
        }

        const tokens = await prisma.token.findMany({
          where: {
            userId: userId,
          },
        });

      if (!code) {
        return reply.status(400).send({ error: "Missing code in body" });
      }
    },
  });
};
