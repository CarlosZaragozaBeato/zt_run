const { registerBrandAccount } = require("../services/api_device.service");
const { PrismaClient } = require("@prisma/client");

const prisma = new PrismaClient();

module.exports = async function (fastify) {
  fastify.get("/device/devices", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
        try{
            const userId = request.user?.id;
            if (!userId){
                return reply.status(401).send({error: "Unauthorized"});
            }
            const userDevices = await prisma.user.findUnique({
                where: {id: userId},
                include: {
                    devices:true,   
                }
            });

            return reply.send({ devices });
        }catch(error){
            return reply.status(500).send({error: "Internal Server Error"});
        }
    },
  });

  fastify.post("/device/register-device", {
    preHandler: fastify.authenticate,
    handler: async (request, reply) => {
      try {
        const { type, brand, model, serial } = request.body;

        const userId = request.user?.id;

        if (!userId) {
          return reply.status(401).send({ error: "Unauthorized" });
        }

        if (!type || !brand) {
          return reply.status(400).send({ error: "Missing type or brand." });
        }

        const device = await prisma.device.create({
          data: {
            type: type,
            brandName: brand,
            model: model,
            serial: serial,
            user: { connect: { id: userId } },
          },
        });

        return reply.status(201).send({ success: true, device });
      } catch (error) {
        console.error(error);
        return reply.status(500).send({ error: "Internal server error" });
      }
    },
  });
};
