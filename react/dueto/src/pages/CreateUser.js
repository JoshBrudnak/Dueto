import React, { Component } from 'react'
import {Input, TextField, Button, Typography, Paper} from 'material-ui'
import {addUser} from '../utils/fetchData.js'

class CreateUser extends Component {
  constructor() {
    super()

    this.state = {
      name: undefined,
      password: undefined,
      repassword: undefined,
      username: undefined, 
      bio: undefined, 
      avatar: undefined,
      age: undefined,
      loc: undefined 
    }
  }

  textChange = (event) => { 
    this.setState({[event.target.name]: event.target.value})
  }

  create = () => {
    const userdata = {
      Name: this.state.name,
      Password: this.state.password,
      Repassword: this.state.repassword,
      Username: this.state.username, 
      Bio: this.state.bio, 
      Age: this.state.age,
      Loc: this.state.loc 
    }
       
    addUser(userdata)
      .then(data => {})
      .catch(error => {})
  }

  profilePicture = () => {
    this.document.getElementById("profilePic").click()
  }

  render() {
    const fieldStyle = {
      marginTop: 10
    }

    return (
      <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{display: "flex", flexDirection: "column", width: 250, padding: 40}}>
          <Typography style={{fontSize: "large"}} variant="heading1">Dueto</Typography>
          <TextField
            label="Name"
            name="name"
            style={fieldStyle}
            value={this.state.name}
            onChange={this.textChange}
          />
          <TextField
            label="Username"
            name="username"
            style={fieldStyle}
            value={this.state.username}
            onChange={this.textChange}
          />
          <TextField
            label="Biography"
            name="bio"
            style={fieldStyle}
            value={this.state.bio}
            onChange={this.textChange}
          />
          <TextField
            label="Age"
            name="age"
            style={fieldStyle}
            value={this.state.age}
            onChange={this.textChange}
          />
          <TextField
            label="Location"
            name="loc"
            style={fieldStyle}
            value={this.state.loc}
            onChange={this.textChange}
          />
          <TextField
            label="Password"
            name="password"
            type="password"
            style={fieldStyle}
            value={this.state.password}
            onChange={this.textChange}
          />
          <TextField
            label="Retype Password"
            name="repassword"
            type="password"
            style={fieldStyle}
            value={this.state.repassword}
            onChange={this.textChange}
          />
          <Typography style={{marginTop: 20}}>Account Avatar</Typography>
          <Input 
            id="profilePic" 
            type="file"
            style={{visibility: "hidden"}} 
            value={this.state.videoName} 
            onChange={this.videoChange}
          />
          <div style={{display: "flex", flexDirection: "row"}}>
            <Button onClick={this.profilePicture}>Profile Picture</Button>
            <Typography style={{marginLeft: 10}}>{this.state.videoName}</Typography> 
          </div>
          <div style={{marginTop: 20}}>
            <Button onClick={this.create}>Create Account</Button>
            <Button onClick={this.cancel}>Cancel</Button>
          </div>
        </Paper>
      </div>
    );
  }
}

export default CreateUser;
