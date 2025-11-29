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
  // OAuth callback: receive ?code from Polar and exchange for token
  fastify.get("/polar/callback", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      const { code, state } = request.query;

      if (!code) {
        return reply
          .status(400)
          .send({ error: "Missing code in query params" });
      }

      const auth = Buffer.from(
        `${POLAR_CLIENT_ID}:${POLAR_CLIENT_SECRET}`
      ).toString("base64");

      const headers = {
        Authorization: `Basic ${auth}`,
        "Content-Type": "application/x-www-form-urlencoded",
        Accept: "application/json",
      };

      const body = {
        grant_type: "authorization_code",
        code: code,
        redirect_uri: POLAR_REDIRECT_URI,
      };

      try {
        const { data } = await axios.post(POLAR_TOKEN_URL, qs.stringify(body), {
          headers,
        });

        const userId = data.x_user_id;
        const filePath = path.join(
          __dirname,
          "..",
          "tokens",
          "polar",
          `${userId}.json`
        );

        fs.writeFileSync(filePath, JSON.stringify(data, null, 2));

        return reply.status(200).send({ saved: true, token: data });
      } catch (error) {
        const errResponse = error.response?.data || { message: error.message };
        return reply.status(500).send({
          error: "Failed to exchange code for token",
          details: errResponse,
        });
      }
    },
  });

  // Exchange code for token
  fastify.post("/polar/token", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      const userId = request.user?.id;

        if (!userId){
          return reply.status(401).send({error: "Unauthorized"});
        }
        polarAdapter.test(userId);        
    },
  });
};
