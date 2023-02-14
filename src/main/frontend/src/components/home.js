import React, { Component } from "react";
import img from "../Images/adminprofile.png";
import Sidebar from "./sidebar";
import Dashboard from "./dashboard";
import ApplicationUpload from "./applicationUpload";
import GenerateNotices from "./generateNotices";
import ProcessNotices from "./processNotices";
import TemplateUpload from "./templateUpload";
import { connect } from "react-redux";
import UserMaintenance from "./userMaintenance";
import axios from "axios";

class Home extends Component {

    constructor(){
        super();
        this.state={
            pathName:'',
            role:'',
        }
    }

    componentDidMount() {
        document.body.style.backgroundColor = "aquamarine"
        axios.get("http://localhost:8080/getRole",{
            params: {
                email: this.props.userInfo.email
            }
        }).then((res)=>{
            this.setState({role:res.data})
        }).catch((error)=>{
            console.log(error)
        })
    }

    render() {
        const path = window.location.pathname;
        this.setState({pathName:path})
        return (
            <React.Fragment>
                <div className="row">
                    <div style={{ width: "20%" }}>
                        <Sidebar role={this.state.role}/>
                    </div>
                    <div style={{ width: "80%" }}>
                        <div className="profileInfo">
                            <img src={img} className="profileImage" alt="profile pic"></img>
                            <div className="profileName">
                                {this.state.role==='S_ADMIN'?<p style={{textAlign:"center"}}>Super Admin</p>:null}
                            </div>
                        </div>
                        {this.state.pathName === "/dashboard" ? <Dashboard /> : null}
                        {this.state.pathName === "/applicationUpload" ? <ApplicationUpload /> : null}
                        {this.state.pathName === "/generateNotices" ? <GenerateNotices /> : null}
                        {this.state.pathName === "/processNotices" ? <ProcessNotices /> : null}
                        {this.state.pathName === "/templateUpload" ? <TemplateUpload /> : null}
                        {this.state.pathName === "/userMaintenance" ? <UserMaintenance /> : null}
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
  
export default connect(mapStateToProps)(Home);