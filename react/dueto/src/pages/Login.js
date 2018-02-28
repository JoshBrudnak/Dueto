import React, { Component } from 'react';
import { TextField } from 'material-ui';

class Login extends Component {
  render() {
    const loginStyle = {}
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

export default Login;
