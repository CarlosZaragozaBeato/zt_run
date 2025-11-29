const axios = require("axios");


const getUserToken = require("../../utils/getUserToken");
const BRAND = "POLAR";
const POLAR_CLIENT_ID = process.env.POLAR_CLIENT_ID;
const POLAR_CLIENT_SECRET = process.env.POLAR_CLIENT_SECRET;
const POLAR_REDIRECT_URI = process.env.POLAR_REDIRECT_URI;
const POLAR_TOKEN_URL = "https://polarremote.com/v2/oauth2/token";



async function exchangeCodeForToken(code) {
  const response = await axios.post(POLAR_TOKEN_URL, null, {
    params: {
      grant_type: "authorization_code",
      code,
      client_id: POLAR_CLIENT_ID,
      client_secret: POLAR_CLIENT_SECRET,
      redirect_uri: POLAR_REDIRECT_URI,
    },
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
  });

  return response.data; // { access_token, refresh_token, expires_in, ... }
}


async function test(userId){
  const token = await getUserToken(userId, BRAND);
}






module.exports = {
  exchangeCodeForToken,
  test
  //   registerAccount,
};
