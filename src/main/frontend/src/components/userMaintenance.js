import React, { Component, Fragment } from "react";
import { Form } from "react-bootstrap";
import axios from "axios";

class UserMaintenance extends Component {

    constructor() {
        super();
        this.state = {
            form: {
                userId: 0,
                userName: '',
                email: '',
                contactNumber: '',
                role: '',
                password: '',
            },
            creationStatus:false,
        }
    }

    handleChange = (event) => {
        let { name, value } = event.target;
        let formDum = this.state.form;
        formDum[name] = value;
        this.setState({ form: formDum });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios.post("http://localhost:8080/createUser", this.state.form).then((res) => {
            if (res.data > 0) {
                this.setState({creationStatus:true})
            }
        }).catch(err => {
            console.log(err.message)
        })
        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = "")
        );
        this.setState({ form: [{}] });
    }

    render() {
        return (
            <Fragment>
                <h2 style={{ textAlign: "center" }}>User Maintenance</h2><br />
                <div className="card applicationUploadCard">
                    <div className="card-body">
                        {this.state.creationStatus ? <h4 className="text-success"> User Created Successfully</h4>:null}
                        <h2>Create User</h2><br />
                        <form onSubmit={this.handleSubmit}>
                            <div className="row">
                                <div style={{ width: "70%" }}>
                                    <div className="form-group">
                                        <label className="labelText">User Name</label>
                                        <input type="text" name="userName" id="userName" className="form-control" placeholder="User Name" onChange={this.handleChange} />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Email</label>
                                        <input type="text" name="email" id="userEmail" className="form-control" placeholder="example@domain.com" onChange={this.handleChange} />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Contact Number</label>
                                        <input type="number" name="contactNumber" id="contactNumber" className="form-control" placeholder="Contact Number" onChange={this.handleChange} />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Select Role</label>
                                        <Form.Select name="role" id="role" onChange={this.handleChange}>
                                            <option>--Select Role--</option>
                                            <option>Admin</option>
                                            <option>Bank</option>
                                            <option>Notice Generator</option>
                                            <option>Notice Approver</option>
                                        </Form.Select>
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Password</label>
                                        <input type="password" name="password" id="createPassword" placeholder="Enter Password" className="form-control" onChange={this.handleChange} />
                                    </div>
                                    <div className="form-group">
                                        <label className="labelText">Confirm Password</label>
                                        <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Enter Password" className="form-control" />
                                    </div><br />
                                    <button className="btn btn-success" style={{ float: "right", marginTop: "3%" }}>Create</button>
                                </div>
                                <div className="col" style={{ width: "30%" }}>
                                    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
                                    <button className="btn btn-primary">Update</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </Fragment>
        )
    }
}

export default UserMaintenance;