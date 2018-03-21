import React, { Component } from 'react'
import {Input, TextField, Button, Typography, Paper} from 'material-ui'
import {Link} from "react-router-dom"
import {getProfileData, updateUser} from '../utils/fetchData.js'

class EditProfile extends Component {
  constructor() {
    super()

    this.state = {
      name: undefined,
      password: undefined,
      repassword: undefined,
      username: undefined, 
      bio: undefined, 
      avatar: undefined,
      avatarName: undefined,
      avatarTitle: undefined,
      nameError: false,
      usernError: false,
      passError: false,
      passError: false,
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        this.setState({
          name: data.Name,
          username: data.UserName,
          desc: data.Desc,
          loc: data.Loc,
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
    if(s.password === undefined || s.password === "") {
      this.setState({passError: true})
      return
    }
    if(s.repassword === undefined || s.repassword === "") {
      this.setState({repassError: true})
      return
    }
 
    if(this.state.nameError || this.state.usernError || this.state.passError || this.state.repassError) {
      return
    }

    const userdata = {
      Name: s.name,
      Password: s.password,
      Repassword: s.repassword,
      Username: s.username,
      Bio: s.bio,
      Age: s.age,
      Loc: s.loc
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
      <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
        <Paper style={{display: "flex", flexDirection: "column", width: 350, padding: 40}}>
          <Typography style={{fontSize: "large"}} variant="heading1">Dueto</Typography>
          <TextField
            name="name"
            error={this.state.nameError}
            style={fieldStyle}
            value={this.state.name}
            onChange={this.textChange}
          />
          <TextField
            name="username"
            error={this.state.usernError}
            style={fieldStyle}
            value={this.state.username}
            onChange={this.textChange}
          />
          <TextField
            name="bio"
            style={fieldStyle}
            value={this.state.bio}
            onChange={this.textChange}
          />
          <TextField
            name="age"
            style={fieldStyle}
            value={this.state.age}
            onChange={this.textChange}
          />
          <TextField
            name="loc"
            style={fieldStyle}
            value={this.state.loc}
            onChange={this.textChange}
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
    );
  }
}

export default EditProfile;
