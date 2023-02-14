import React, { Component, Fragment } from "react";
import { Form, Table } from "react-bootstrap";
import axios from "axios";

class ProcessNotices extends Component {

    constructor(){
        super();
        this.state={
            selectedLotNumber:'',
            lotNumber:[],
            loanNumber:[],
        }
    }

    handleDate=(selectedDate)=>{
        axios.get("http://localhost:8080/getLotNumbersByDate/"+selectedDate).then((res)=>{
            this.setState({lotNumber:[...res.data]})
        }).catch((error)=>{
            console.log(error)
        })
    }

    handleLotNumber=(selectedLotNum)=>{
        this.setState({selectedLotNumber:selectedLotNum})
        axios.get("http://localhost:8080/getAllLoanNumbers", {
            params: {
                lotNumber: selectedLotNum
            }
        }).then((res) => {
            this.setState({ loanNumber: [...res.data] });
        }).catch((error) => {
            console.log(error);
        })
    }
    
    printAll=()=>{
        // axios.get("http://localhost:8080/printAll/"+this.state.selectedLotNumber).then((res) => {
        //     // window.open();
        // }).catch((error) => {
        //     console.log(error);
        // })
        window.open("http://localhost:8080/printAll/"+this.state.selectedLotNumber)
    }

    handleClick = () => {
        window.open("http://localhost:8080/getNotice/EMI_100123_B8_0001.pdf")
    }

    render() {
        return (
            <Fragment>
                <h1 style={{ textAlign: "center" }}>Print Notices</h1>
                <br />
                <div className="row">
                    <div className="col">
                        <label>Date</label>
                        <input type="date" className="form-control"  onChange={(event)=>this.handleDate(event.target.value)}/>
                    </div>
                    <div className="col">
                    <label>Bank Name</label>
                        <Form.Select disabled>
                            <option>--select Bank Name--</option>
                        </Form.Select>
                    </div>
                    <div className="col">
                        <label>Lot Number</label>
                        <Form.Select   onChange={(event)=>this.handleLotNumber(event.target.value)}>
                            <option>--Select Lot Number--</option>
                            {this.state.lotNumber.length > 0 ?
                                    this.state.lotNumber.map((value) =>
                                        <option>{value}</option>
                                    ) : null}
                        </Form.Select>
                    </div>
                </div><br/>
                <div className="generateNoticeTable">
                <div className="detailsTable">
                        <Table striped variant="light">
                            <thead>
                                <tr>
                                    <th>S.N.</th>
                                    <th>Reference Number</th>
                                    <th>Customer Name</th>
                                    <th>Loan Number</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.loanNumber.map((value, index) =>
                                    <tr>
                                        <td>{index + 1}</td>
                                        <td>{value.referenceNumber}</td>
                                        <td>{value.customerName}</td>
                                        <td>{value.loanNumber}</td>
                                    </tr>
                                )}
                            </tbody>
                        </Table>
                    </div><br/>
                    <div className="generateNoticesButtons"> 
                        <input type="checkbox"></input>&nbsp;
                        <label className="labelText">Email</label>&nbsp;
                        <input type="checkbox"></input>&nbsp;
                        <label className="labelText">Whatsapp</label><br/><br/>
                        <button className="btn btn-primary" onClick={()=>this.printAll()}>Print All</button>&nbsp;
                        <button className="btn btn-success" onClick={() => this.handleClick()}>Print</button>&nbsp;
                        <button className="btn btn-danger">Cancel</button>
                    </div>
                </div>
            </Fragment>
        )
    }
}

export default ProcessNotices;