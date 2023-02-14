import React from 'react';
import { BiGridAlt, BiCloudUpload, BiFileBlank, BiPowerOff,BiUser } from "react-icons/bi";
import { HiFire } from "react-icons/hi";
import { NavLink } from 'react-router-dom';
import logo from "../Images/logo.jpg"
const Sidebar = (props) => {
    return (
        <div className="sideBarHome">
            <h2 className="sideBarHeading"><img src={logo} alt="logo" className="logo"/> Sachniti</h2>
            <NavLink to="/dashboard" className="sideBarLinks">
                <BiGridAlt className="sideBarIcons" />&nbsp;&nbsp;Dashboard
            </NavLink>
            <NavLink to="/applicationUpload" className="sideBarLinks">
                <BiCloudUpload className="sideBarIcons" />&nbsp;&nbsp;Application Upload
            </NavLink>
            <NavLink to="/generateNotices" className="sideBarLinks">
                <BiFileBlank className="sideBarIcons" />&nbsp;&nbsp; Generate Notices
            </NavLink>
            <NavLink to="/processNotices" className="sideBarLinks">
                <BiFileBlank className="sideBarIcons" />&nbsp;&nbsp; Process Notices
            </NavLink>
            <NavLink to="/templateUpload" className="sideBarLinks">
                <HiFire className="sideBarIcons" />&nbsp;&nbsp; Template Upload
            </NavLink>
            <NavLink to="/userMaintenance" className="sideBarLinks">
                <BiUser className="sideBarIcons" />&nbsp;&nbsp; User Maintenance
            </NavLink>
            <a href="/" className="sideBarLinks"><BiPowerOff className="sideBarIcons"/>&nbsp;&nbsp; Log out </a><br />
        </div>
    );
};
export default Sidebar;