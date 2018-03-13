import React, { Component } from 'react'
import {Input, Button, TextField} from 'material-ui'
import Header from '../component/Header.js'
import {getProfileData} from '../utils/fetchData.js'

class Profile extends Component {
  constructor() {
    super()
   
    this.state = {
      videoName: undefined,
      name: undefined,
      video: undefined,
      desc: "",
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        console.log(data)
      })
      .catch(error => {})
  }

  render() {
    return (
      <div>
        <Header/>
        <div>
          <Avatar alt="Remy Sharp" src="/static/images/remy.jpg"/>
        </div>
      </div>
    )
  }
}

export default Profile;
