import React, { Component, Fragment } from "react";
import { BiGrid, BiMenu, BiCart, BiRightArrow } from "react-icons/bi";

class Dashboard extends Component {

    render() {
        return (
            <Fragment>
                <h1 style={{ textAlign: "center" }}>Summary</h1>
                <br />
                <div className="row">
                    <div className="col dashBoardCard">
                        <div className="card summaryCard" style={{backgroundColor:"aliceblue"}}>
                            <BiGrid className="cardIcons"></BiGrid>
                            <p className="attributeName">Application Received</p>
                            <h2 className="attributeName">23423</h2>
                        </div>
                    </div>
                    <div className="col dashBoardCard">
                        <div className="card summaryCard" style={{backgroundColor:"rgb(271,270,271)"}}>
                            <BiMenu className="cardIcons" color="lightgreen"></BiMenu>
                            <p className="attributeName">Notices Generated</p>
                            <h2 className="attributeName">2342</h2>
                        </div>
                    </div>
                    <div className="col dashBoardCard">
                        <div className="card summaryCard" style={{backgroundColor:"aliceblue"}} >
                            <BiRightArrow className="cardIcons"></BiRightArrow>
                            <p className="attributeName">Notice Pending Aproval</p>
                            <h2 className="attributeName">1433</h2>
                        </div>
                    </div>
                    <div className="col dashBoardCard">
                        <div className="card summaryCard" style={{backgroundColor:"aliceblue"}}>
                            <BiCart className="cardIcons"></BiCart>
                            <p className="attributeName">Notice/Email sent</p>
                            <h2 className="attributeName">433</h2>
                        </div>
                    </div>
                </div>
            </Fragment>
        )
    }
}

export default Dashboard;