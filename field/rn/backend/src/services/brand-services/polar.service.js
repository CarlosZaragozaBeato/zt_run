

const polarAdapter = require("../adapters/polar.adapter");


module.exports = {
    async register(email, password){
        polarAdapter.registerAccount();
    }
}