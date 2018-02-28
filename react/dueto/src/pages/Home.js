import React, { Component } from 'react'
import { Paper, Tabs, Tab, IconButton, TextField } from 'material-ui'
import {Link} from 'react-router-dom'
import AccountCircle from 'material-ui-icons/AccountCircle'

class Home extends Component {
  loginClick() {

  }

  render() {
    return (
        <Paper style={{backgroundColor: "grey", display: "flex", justifyContent: "space-between"}}>
          <div>
            <Tab style={{width: 100}} label="Home" />
            <Tab style={{width: 100}} label="Profile" />
            <Tab style={{width: 100}} label="Categories"/>
          </div>
          <div style={{align: "right", display: "flex"}}>
            <TextField id="search" type="search" margin="normal"/>
            <Link to="/login">
              <IconButton>
                <AccountCircle/> 
              </IconButton>
            </Link>
           </div>
        </Paper>
    );
  }
}

export default Home;
