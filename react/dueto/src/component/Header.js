import React, { Component } from 'react'
import { Paper, Tab, Tabs, Button, TextField } from 'material-ui'
import {Link} from 'react-router-dom'
import {getSessionId, logoutUser, eraseCookie} from '../utils/fetchData.js'

class Header extends Component {
  constructor() {
    super()
  
    this.state = {
      auth: false
    }
  }

  componentDidMount() {
    let session = getSessionId

    if(session !== "") {
      this.setState({auth: true})
    }
    else {
      this.setState({auth: false})
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
        eraseCookie()
        window.location = "/logout"
      })
      .catch(error => {
      })
  }
 
  getAuthButton = () => {
    if(this.state.auth) {
      return (
        <Button onClick={this.logout}>Logout</Button>
      )
    }
    else {
      return (
        <Button onClick={this.login}>Login</Button>
      )
    }
  }

  render() {
    return (
      <div>
        <Paper style={{display: "grid"}}>
          <div style={{gridColumn: 2}}>
            <Tabs onChange={this.tabChange}>
              <Tab style={{width: 100}} label="Home" />
              <Tab style={{width: 100}} label="Profile" />
              <Tab style={{width: 100}} label="Discover"/>
            </Tabs>
          </div>
          <div style={{gridColumn: 3}}>
            <TextField id="search" type="search" margin="normal"/>
            {this.getAuthButton()}
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
