import React, { Component } from 'react'
import { Paper, Tab, Tabs, Button, TextField } from 'material-ui'
import AccountCircle from 'material-ui-icons/AccountCircle'
import {Link} from 'react-router-dom'

class Header extends Component {
  constructor() {
    super() 

    this.state = {
      tab: undefined
    }
  }

  tabChange() {

  }

  render() {
    const {tab} = this.state

    return (
      <div>
        <Paper style={{display: "grid"}}>
          <div style={{gridColumn: 2}}>
            <Tabs value={tab} onChange={this.tabChange}>
              <Tab style={{width: 100}} label="Home" />
              <Tab style={{width: 100}} label="Profile" />
              <Tab style={{width: 100}} label="Categories"/>
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
      </div>
    )
  }
}

export default Header
