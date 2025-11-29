#!/usr/bin/env node

const fs = require("fs");
const path = require("path");
const readLine = require("readline");
const { setupFrontend } = require("./setup_front.js");
const { setupBackend } = require("./setup_back.js");

function ask(question) {
	return new Promise((resolve) => {
		const rl = readLine.createInterface({
			input: process.stdin,
			output: process.stdout,
		});

		rl.question(question, (answer) => {
			rl.close();
			resolve(answer.trim());
		});
	});
}
function crearProyecto(projectName){
 if (!projectName) {
		console.error("❌ Debes ingresar un nombre.");
		process.exit(1);
	}

	const rootPath = path.join(__dirname, "..", projectName);

	if (fs.existsSync(rootPath)) {
		console.error("La carpeta ya existe!!");
		process.exit(1);
	}

	fs.mkdirSync(rootPath);
	console.log("Carpeta Creada: ", rootPath);

	// INSTALACION FRONTEND
	const shouldInstallFrontEnd = await ask("¿Quieres instalar FrontApp(y/n)?");
	if (shouldInstallFrontEnd == "y") {
		await setupFrontend(rootPath);
		console.log("✅ Frontend creado en:", path.join(rootPath, "frontend"));
	}

	const shouldInstallBackEnd = await ask("¿Quieres instalar BackEnd(y/n)?");
	if (shouldInstallBackEnd == "y") {
		await setupBackend(rootPath);
		console.log(
			"✅ Backend creado en:",
			path.join(rootPath, "shouldInstallBackEnd"),
		);
	}
}

(async () => {
	const projectName = await ask("📦 Nombre del proyecto raíz: ");
    const typeProject = await ask("¿Que quieres hacer?\n\t1.MODIFICAR\n\t2.CREAR") 
    
    const n =  isNaN( parseInt(typeProject, 10)) ? null :  parseInt(typeProject, 10);   
    console.log(n)
    switch (typeProject){
        case 1:

            break;

        case 2:

            break;

        default:
            
    }
    
   })();
