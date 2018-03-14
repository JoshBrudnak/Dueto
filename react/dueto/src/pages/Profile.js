import React, { Component } from 'react'
import {Avatar} from 'material-ui'
import Header from '../component/Header.js'
import {getProfileData} from '../utils/fetchData.js'

class Profile extends Component {
  constructor() {
    super()
   
    this.state = {
      name: "",
      username: "",
      desc: "",
      id: undefined,
      likes: undefined,
      followers: undefined,
      avatarUrl: "/api/avatar?artist=0" 
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        let avUrl = "/api/avatar?artist=" + data.Id

        this.setState({
          name: data.Name,
          username: data.UserName,
          desc: data.Desc,
          followers: data.FollowerCount,
          likes: data.LikeCount,
          avatarUrl: avUrl 
        })
      })
      .catch(error => {})
  }

  render() {
    return (
      <div>
        <Header/>
        <div>
          <Avatar src={this.state.avatarUrl}/>
        </div>
      </div>
    )
  }
}

export default Profile;
