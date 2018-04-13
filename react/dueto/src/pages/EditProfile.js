import React, { Component } from 'react'
import {Input, TextField, Button, Typography, Paper} from 'material-ui'
import {Link} from "react-router-dom"
import Header from "../component/Header.js"
import {getProfileData, updateUser} from '../utils/fetchData.js'

class EditProfile extends Component {
  constructor() {
    super()

    this.state = {
      name: undefined,
      password: "",
      repassword: "",
      username: undefined,
      desc: undefined,
      email: undefined,
      avatar: undefined,
      avatarName: undefined,
      avatarTitle: undefined,
      nameError: false,
      usernError: false,
      passError: false,
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        this.setState({
          name: data.Name,
          username: data.Username,
          desc: data.Desc,
          email: data.Email
        })
      })
      .catch(error => {
        console.error(error)
      })

  }

  textChange = (event) => { 
    this.setState({[event.target.name]: event.target.value})
  }

  update = () => {
    const s = this.state
    
    if(s.name === undefined || s.name === "") {
      this.setState({nameError: true})
      return
    }
    if(s.username === undefined || s.username === "") {
      this.setState({usernError: true})
      return
    }
 
    if(this.state.nameError || this.state.usernError) {
      return
    }

    const userdata = {
      Name: s.name,
      Password: s.password,
      Repassword: s.repassword,
      Username: s.username,
      Bio: s.desc
    }
       
    updateUser(userdata)
      .then(data => {
        window.location = "/home"
      })
      .catch(error => { 
         console.error(error)
      })
  }

  avatarChange = (event) => {
    let file = event.target.files[0]
    this.setState({avatarPath: event.target.value, avatarName: file.name, avatar: file})
  }

  profilePicture = () => {
    document.getElementById("profilePic").click()
  }

  cancel = () => {
    document.getElementById("cancel").click()
  }
     

  render() {
    const fieldStyle = {
      marginTop: 10
    }

    return (
      <div>
        <Header/>
        <div style={{height: "-webkit-fill-available", backgroundColor: "#e8e8e8", display: "flex", flexDirection: "column", alignItems: "center"}}>
          <Paper style={{display: "flex", flexDirection: "column", width: 350, margin: 40, padding: 40}}>
            <Typography style={{fontSize: "large"}} variant="heading1">Dueto</Typography>
            <TextField
              name="name"
              error={this.state.nameError}
              style={fieldStyle}
              defaultValue={this.state.name}
              value={this.state.name}
              onChange={this.textChange}
              helperText="Name"
            />
            <TextField
              name="username"
              error={this.state.usernError}
              style={fieldStyle}
              defaultValue={this.state.username}
              value={this.state.username}
              onChange={this.textChange}
              helperText="Username"
            />
            <TextField
              name="desc"
              style={fieldStyle}
              defaultValue={this.state.desc}
              value={this.state.desc}
              onChange={this.textChange}
              helperText="Biography"
            />
            <TextField
              name="email"
              style={fieldStyle}
              defaultValue={this.state.email}
              value={this.state.email}
              onChange={this.textChange}
              helperText="E-mail"
            />
            <TextField
              label="Password"
              name="password"
              type="password"
              error={this.state.passError}
              style={fieldStyle}
              value={this.state.password}
              onChange={this.textChange}
            />
            <TextField
              label="Retype Password"
              name="repassword"
              type="password"
              error={this.state.repassError}
              style={fieldStyle}
              value={this.state.repassword}
              onChange={this.textChange}
            />
            <div style={{display: "flex", flexDirection: "row", alignItems: "baseline", marginTop: 20}}>
              <Button onClick={this.profilePicture}>Profile Picture</Button>
              <Typography style={{marginLeft: 10}}>{this.state.avatarName}</Typography> 
            </div>
            <Input 
              id="profilePic"
              type="file"
              allow=".jpeg"
              style={{visibility: "collapse"}} 
              value={this.state.avatarPath} 
              onChange={this.avatarChange}
            />
            <div style={{marginTop: 10}}>
              <Button onClick={this.create}>Update Account</Button>
              <Button onClick={this.cancel}>Cancel</Button>
            </div>
          </Paper>
          <Link style={{visibility: "collapse"}}  id="cancel" to="/profile"/>
        </div>
      </div>
    )
  }
}

export default EditProfile;
