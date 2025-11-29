// const bcrypt = require("bcrypt");
// const jwt = require("jsonwebtoken");
const { PrismaClient, DeviceBrand } = require("@prisma/client");

// SERVICES
const polarService = require("./brand-services/polar.service");
// const garminService = require("../services/garmin.service");
// const corosService = require("../services/coros.service");
// const suuntoService = require("../services/suunto.service");
// const fitbitService = require("../services/fitbit.service");

const prisma = new PrismaClient();

const brandServices = {
  POLAR: polarService,
  // GARMIN: garminService,
  // COROS: corosService,
  // SUUNTO: suuntoService,
  // FITBIT: fitbitService,
};

async function registerBrandAccount(email, password, brand) {
  if (!Object.values(DeviceBrand).includes(brand)) {
    throw new Error(`Marca no soportada ${brand}`);
  }

  const service = brandServices[brand];

  if (!service || !service.register) {
    throw new Error(`Servicio no disponible para la marca ${brand}`);
  }
  const hashedPassword = await bcrypt.hash(password, 10);

  const brandResult = await service.register(email, hashedPassword);

  const brandAccount = await prisma.brandAccount.create({
    data: {
      name: brand,
      email: email,
      password: hashedPassword,
      integrationActive: brandResult,
    },
  });

  return brandAccount;
}

module.exports = { registerBrandAccount };
