import React, { Component } from 'react'
import { Paper, Tab, Tabs, Button} from 'material-ui'
import {Link} from 'react-router-dom'
import {logoutUser} from '../utils/fetchData.js'

class Header extends Component {
  constructor() {
    super()
  
    this.state = {
      auth: false
    }
  }

  tabChange = (event, value) => {
    switch(value) {
      case 0:
        document.getElementById("home").click()
        break;
      case 1:
        document.getElementById("profile").click()
        break;
      case 2:
        document.getElementById("discover").click()
        break;
      default:
        break;
    } 
  }

  login = () => {
    document.getElementById("login").click()
  }
 
  logout = () => {
    logoutUser()
      .then(data => {
        window.location = "/logout"
      })
      .catch(error => {
      })
  }

  render() {
    return (
      <div>
        <Paper style={{display: "grid", padding: 20, backgroundColor: "#29507a"}}>
          <Link to="/home">
            <img style={{width: 150, height: 150}} src="/resource/dueto.png" alt="Dueto"/>
          </Link>
          <div style={{color: "#e8e8e8", gridColumn: 2, display: "flex", alignItems: "flex-end", justifyContent: "center"}}>
            <Tabs onChange={this.tabChange}>
              <Tab style={{width: 100}} label="Home" />
              <Tab style={{width: 100}} label="Profile" />
              <Tab style={{width: 100}} label="Discover"/>
            </Tabs>
          </div>
          <div style={{gridColumn: 3, display: "flex", justifyContent: "flex-end"}}>
            <Button style={{color: "#e8e8e8", height: "fit-content"}}  onClick={this.logout}>Logout</Button>
          </div>
        </Paper>
        <Link id="home" to="/home" style={{visibility: "hidden"}}/>
        <Link id="profile" to="/profile" style={{visibility: "hidden"}}/>
        <Link id="discover" to="/discover" style={{visibility: "hidden"}}/>
        <Link id="logout" to="/logout" style={{visibility: "collapse"}}/>
        <Link id="login" to="/login" style={{visibility: "collapse"}}/>
      </div>
    )
  }
}

export default Header
