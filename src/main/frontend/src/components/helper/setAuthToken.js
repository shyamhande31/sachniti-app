import axios from 'axios';
 
export const setAuthToken = token => {
   if (token) {
       console.log("setted")
       axios.defaults.headers.common["Authorization"] = token;
   }
   else
       delete axios.defaults.headers.common["Authorization"];
}