import React, { Component } from 'react'
import {Typography, Paper} from 'material-ui'

class Logout extends Component {
  render() {
    return (
      <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{margin: 40, padding: 20, display: "flex", flexDirection: "column", alignItems: "center"}}>
          <Typography style={{marginBottom: 20, fontSize: "large"}}>Logout Successful</Typography>
          <Typography style={{fontSize: "large"}}>You are now logged out of Dueto</Typography>
        </Paper>
      </div>
    );
  }
}

export default Logout;
