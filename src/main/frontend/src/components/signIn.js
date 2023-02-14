import React, { Component } from "react";
import { BiLock, BiEnvelope } from "react-icons/bi";
import { Navigate } from 'react-router-dom';
import logo from "../Images/logo.jpg";
import axios from "axios";
import { connect } from "react-redux";

var captchValue='';

function createActionObj(typeVal,payLoadVal){
    return{
        type:typeVal,
        payLoad:payLoadVal
    }
}

class SignIn extends Component {
    constructor() {
        super();
        captchValue=this.generateString(6)
        this.state = {
            signInStatus: false,
            form: {
                email: "",
                password: "",
            },
            captcha:"",
        }
    }

    handleChange = (event) => {
        let { name, value } = event.target;
        let formDum=this.state.form;
        formDum[name] = value;
        this.setState({ form: formDum });
    }

    handleCaptcha=(event)=>{
        this.setState({captcha:event.target.value})
    }

    handleSubmit = (event) => {
        event.preventDefault();
        if(this.state.captcha!==captchValue){
            alert("Invalid Captch!!!");
            window.location.reload();
        }
        axios.post("http://localhost:8080/authenticate",this.state.form).then((res)=>{
            let actionObj=createActionObj('user',res.data)
            this.props.dispatch(actionObj)
            this.setState({signInStatus:true})
        }).catch(err=>{
            alert("Invalid Credentials")
        })
    }

    componentDidMount() {
        document.body.style.backgroundColor = "aquamarine";
    }

    generateString=(length)=>{
        let result='';
        const char='abc123';
        const charLength=6;
        for(let i=0;i<length;i++){
            result+=char.charAt(Math.floor(Math.random()*charLength));
        }
        return result;
    }

    render() {
        if (this.state.signInStatus && this.props.userInfo.token) {
            return <Navigate replace to="/dashboard" />;
        }
        return (
            <React.Fragment>
                <div className="card signInCard">
                    <div className="card-body">
                        <h1><img src={logo} alt="logo" className="logo" /> Sachniti</h1>
                        <br />
                        <h2>Sign in</h2>
                        <br />
                        <form onSubmit={this.handleSubmit}>
                            <div className="form-group">
                                <label className="labelText">User Name</label>
                                <div className="input-icons">
                                    <i className="icon"><BiEnvelope /></i>
                                    <input type="text" className="form-control" name="email" id="email" onChange={this.handleChange} />
                                </div>
                            </div>
                            <div className="form-group">
                                <label className="labelText">Password</label>
                                <div className="input-icons">
                                    <i className="icon"><BiLock /></i>
                                    <input type="password" className="form-control" name="password" id="password" onChange={this.handleChange} />
                                </div>
                            </div>
                            <div className="form-group">
                                <label className="labelText">Captcha</label>
                                <div className="input-icons">
                                    <i className="icon"><BiLock /></i>
                                    <input type="text" className="form-control" name="captcha" id="captcha" onChange={this.handleCaptcha} />
                                </div>
                            </div><br/>
                            <div className="captchaBox">
                                <h4>{captchValue}</h4>
                            </div>
                            <div className="row">
                                <div className="col-lg-5">
                                    <input type="checkbox" />&nbsp;
                                    <span>Remember me</span>
                                </div>
                                <div className="col-lg-2"></div>
                                <div className="col-lg-5">
                                    <a href="#">Forgot password?</a>
                                </div>
                            </div>
                            <br />
                            <button type="submit" className="signInButton">SignIn</button>
                        </form>
                    </div>
                </div>
            </React.Fragment>
        )
    }
}

function mapStateToProps(value){
    let {userInfo} =value;
    return {userInfo};
  }
  
export default connect(mapStateToProps)(SignIn);