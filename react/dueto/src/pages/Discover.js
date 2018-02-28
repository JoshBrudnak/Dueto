import React, { Component } from 'react';
import { Paper, Tabs, Tab, IconButton, TextField } from 'material-ui';
import AccountCircle from 'material-ui-icons/AccountCircle';

class Discover extends Component {
  render() {
    return (
      <div>
        <div>
          <TextField
            label="UserName"
            type="password"
            margin="normal"
          />
          <TextField
            label="Password"
            type="password"
            margin="normal"
          />
        </div>
      </div>
    );
  }
}

export default Discover;
