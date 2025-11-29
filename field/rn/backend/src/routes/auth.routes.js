
const { login, register} = require("../services/auth.service");
const { z } = require("zod");

module.exports = async function (fastify) {
    fastify.post("/login", async (request, reply) => {
        try{
            const schema = z.object({
                email: z.string().email(),
                password: z.string().min(6)
            });

            const {email, password} = schema.parse(request.body);
            const { token } = await login(email, password);

            reply.send({ token });
        }catch (err){
            reply.code(401).send({error: err.message });
        }
    });


    fastify.post("/register", async(request, reply) => {
        try{
            const schema = z.object({
                email: z.string().email(),
                password: z.string().min(6),
            });

            const { email, password } = schema.parse(request.body);
            const { token } = await register(email, password);

            reply.code(201).send({ token });
        }catch(err){
            reply.code(400).send({error: err.message });
        }
    });

};


