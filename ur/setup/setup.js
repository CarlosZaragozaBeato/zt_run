#!/usr/bin/env node

const fs = require("fs");
const path = require("path");
const readLine = require("readline");
const { setupFrontend } = require("./setup_front.js");
const { setupBackend } = require("./setup_back.js");
const DATA_PROJECT = require("./setup.json");
const { features } = require("process");

const VERSION = DATA_PROJECT["VERSION"];
const PROJECT = DATA_PROJECT["PROJECT"];
const MESSAGE_OPTIONS = "¿Que quieres hacer?\n\t1.MODIFICAR\n\t2.CREAR\n\n";
const MESSAGE_TYPE = "¿Que quieres hacer?\n\t1. ADD \n\t2. MODIFICAR\n\t3.ELIMINAR\n\n";

/***
 * FUNCIONES GENERALES
 *
 *
 * */
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

function setupProject(projectName, rootPath){
    // CONFIGURACION DE SETUP 
    const jsonPath = path.join(rootPath, "stZensyra.json");
	fs.writeFile(jsonPath, JSON.stringify([], null, 2), (err)=>{
        if (err){
            console.error(`Error al crear archivo propiedades ${err}\n`);
            process.exit(1);
        }else{
            console.log("Archivo de propiedades creado con existo.\n");
        }
    });
 
    return {
        "projectName": projectName,
        "versionSetup": VERSION,
        "createdBy": PROJECT,
        "created":new Date().toLocaleString(),
        "features":[],
        "lastUpdate":new Date().toLocaleString(),
    }, jsonPath
}
function sleep(ms){
    return new Promise(resolve => setTimeout(resolve, ms));
}
//////////////////////////////////////////////////////////////////////


/** INTERACCION AND SETUP PROJECT
 *
 *
 *
 */
function optionsSettings(idOption, type){
    switch(type){
        case 1: //"add":

            break;

        case 2: // "delete"

            break;

        case 3: // "update"
    
            break;
    }
}

function generateTypeFeaturesMessage(){
    const grouped = {};
    for (const feature of DATA_PROJECT["features"]) {
        const key = feature.type;
        if (!grouped[key] || feature.active) {
            grouped[key] = feature;
        }
    }
    const typeFeatures = Object.values(grouped);
    const messageFeatures = typeFeatures
                        .map((feature)=> (`\t${feature.type}\n`));
    return messageFeatures;
}

async function generateInteraction(data){
    let cond = true;
    const featuresActive = generateTypeFeaturesMessage();
    const featureOptionSelected = await ask(`SELECCIONE UN TIPO DE FEATURE:\n${featuresActive}\tExit.\n`);
    

    const projects = generateMessageForProject(featureOptionSelected);
    if (projects.length > 0){
        const typeProject = projects   
                            .map((project)=>`\t${project.id} - ${project.name}\n`);
        const project = ask(`SELECCIONE UN PROYECTO \n${typeProject}`)
        
        const projectSelected = DATA_PROJECT["features"].filter((projectTmp) => projectTmp.id == project | projectTmp.name == project);
        if (projectSelected.length > 0){
            
            data["features"].push({
                    "id": projectSelected["id"],
                    "name": projectSelected["name"],
                    "type": featureOptionSelected
            });
        }else{cond=false;}
    }else{cond = false;}
    
    return [cond, data];
}

function generateMessageForProject(typeFeature){
    const projects = {};
    for (const feature of DATA_PROJECT["features"]){
        if (typeFeature === feature.type & feature.active){
          projects[feature.id] = feature.name;  
        }
    }
    return projects;

}
//////////////////////////////////////////////////////////////////////


/***
 * funciones proceso
 * */
async function crearProyecto(projectName){
   	const rootPath = path.join(__dirname, "../..", projectName);
  
	if (fs.existsSync(rootPath)) {
		console.error("La carpeta ya existe!!");
		process.exit(1);
	}
	fs.mkdirSync(rootPath);
	console.log("Carpeta Creada: ", rootPath);

    // CONFIGURACION SETUP
    const {data,jsonPath} = setupProject(projectName, rootPath);
    await sleep(2000);
    // FUNCIONAMIENTO
    let typeFeaturesProjectCond = false;
    let dataTemp = [false, data]
    do{
        dataTemp = await generateInteraction(data);
        typeFeaturesProjectCond = dataTemp[0];
    }while(typeFeaturesProjectCond);
    
    fs.writeFileSync(jsonPath, JSON.stringify(dataTemp[1], null, 2), "utf8");  
    
}
//////////////////////////////////////////////////////////////////////
async function modificarProyecto(projectName){
    console.log("MODIFICANDO PROYECTO");
    
   	const rootPath = path.join(__dirname, "../..", projectName);
    const jsonPath = path.join(rootPath, "stZensyra.json");
	
    if (!fs.existsSync(rootPath) || !fs.existsSync(jsonPath)) {
		console.error("La carpeta o el archivo de propiedades no existen");
		process.exit(1);
	}

    jsonMod = require(jsonPath);

    const features = DATA_PROJECT["features"].filter((feature) => feature["active"]);
    
    let conditionUser = false;
    
    messageFeatures = features
                        .map((feature)=> ( `${feature["id"]}. ${feature["name"]}`))
                        .join("-1 Exit\n");

    /*message = `SELECCIONE UNA: \n${messageFeatures}\n`;
    do {
        answerMod = await ask(message);
        conditionUser =  isNaN( parseInt(answerMod, 10)) ? false :  parseInt(answerMod, 10);   

        if (conditionUser){
            const featureSelected = features.filter((feature) => feature.id == conditionUser);
            const optionsMod = await ask(MESSAGE_OPTIONS)
            const optionsModParser =  isNaN(parseInt(optionsMod, 10)) ? false :  parseInt(optionsMod, 10);   
            

            switch (optionsModParser){
                case

            }
            if (optionsModParser ){
                   

            }
        }
    }while(conditionUser);
    */
}
//////////////////////////////////////////////////////////////////////
(async () => {
	const projectName = await ask("📦 Nombre del proyecto raíz: ");
    
    if (!projectName) {
		console.error("❌ Debes ingresar un nombre.");
		process.exit(1);
	}

    const typeProject = await ask(MESSAGE_OPTIONS); 
    
    const n =  isNaN( parseInt(typeProject, 10)) ? null :  parseInt(typeProject, 10);   
    
    switch (n){
        case 1:
            await modificarProyecto(projectName);
            break;

        case 2:
            await crearProyecto(projectName);
            break;
    
        case 3:
            // await ;
            break;
        default:
            console.log("Seleccione una opcion correcta.");
            
    }
    
   })();
