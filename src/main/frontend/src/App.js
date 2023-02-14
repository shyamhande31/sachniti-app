import React, { Component, Fragment } from 'react';
import { BrowserRouter, Route, Routes, Navigate } from 'react-router-dom';
import './App.css';
import Home from './components/home';
import SignIn from './components/signIn';
import { setAuthToken } from './components/helper/setAuthToken';
import { connect } from 'react-redux';
import ErrorPage from "./components/errorPage"

class App extends Component {

  render() {
    const token = this.props.userInfo.token;
    return (
      <Fragment>
        <BrowserRouter>
          <Routes>
            <Route exact path="/" element={<Navigate replace to="/signIn" />} />
            <Route exact path="/signIn" element={<SignIn />} />
            {token!=null?<Route exact path="/dashboard" element={<Home />} />:<Route exact path="/dashboard" element={<ErrorPage />} />}
            {token!=null?<Route exact path="/applicationUpload" element={<Home />} />:<Route exact path="/applicationUpload" element={<ErrorPage />} />}
            {token!=null?<Route exact path="/generateNotices" element={<Home />} />:<Route exact path="/generateNotices" element={<ErrorPage />} />}
            {token!=null?<Route exact path="/processNotices" element={<Home />} />:<Route exact path="/processNotices" element={<ErrorPage />} />}
            {token!=null?<Route exact path="/templateUpload" element={<Home />} />:<Route exact path="/templateUpload" element={<ErrorPage />} />}
            {token!=null?<Route exact path="/userMaintenance" element={<Home />} />:<Route exact path="/userMaintenance" element={<ErrorPage />} />}
            <Route exact path="*" element={<Navigate replace to="/" />} />
          </Routes>
        </BrowserRouter>
      </Fragment>
    )
  }
}

function mapStateToProps(value){
  let {userInfo} =value;
  return {userInfo};
}

export default connect(mapStateToProps)(App);
