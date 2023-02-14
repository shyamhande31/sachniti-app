import { combineReducers } from "redux";

let user={}

function userInfo(state=user,action){
    if(action.type==='user'){
        return action.payLoad;
    }
    return user;
}

export default combineReducers(
    {
        userInfo,
    }
);