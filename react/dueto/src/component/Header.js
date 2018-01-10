import React, { Component } from 'react';
import { Paper, Tabs, Tab, IconButton, TextField } from 'material-ui';
import AccountCircle from 'material-ui-icons/AccountCircle';

class Header extends Component {
  render() {
    return (
        <Paper>
            <Tabs centered >
                <Tab style={{width: 100}} label="Home" />
                <Tab style={{width: 100}} label="Profile" />
                <Tab style={{width: 100}} label="Categories"/>
            </Tabs>
            <TextField
              id="search"
              type="search"
              margin="normal"
            />
            <IconButton>
                <AccountCircle/> 
            </IconButton>
        </Paper>
    );
  }
}

export default Header;
