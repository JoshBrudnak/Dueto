import React, { Component } from 'react'
import { Paper, Tab, Tabs, Button, TextField } from 'material-ui'
import {Link} from 'react-router-dom'

class Header extends Component {
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
            <Link to="/login">
              <Button>
                Login
              </Button>
            </Link>
            <Link to="/createuser">
              <Button>
                Create User 
              </Button>
            </Link>
          </div>
        </Paper>
        <Link id="home" to="/home" style={{visibility: "hidden"}}/>
        <Link id="profile" to="/profile" style={{visibility: "hidden"}}/>
        <Link id="discover" to="/discover" style={{visibility: "hidden"}}/>
      </div>
    )
  }
}

export default Header
