module.exports = async function (fastify, opts) {
  fastify.get("/status", async (request, reply) => {
    return { message: "API backend funcionando 🚀" };
  });
};