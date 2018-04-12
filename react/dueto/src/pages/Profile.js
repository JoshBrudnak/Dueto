import React, { Component } from 'react'
import {Avatar, Typography, Paper, IconButton, Tabs, Tab} from 'material-ui'
import Header from '../component/Header.js'
import {AddCircle, Settings} from 'material-ui-icons'
import {Link} from 'react-router-dom'
import VideoCard from '../component/VideoCard.js'
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
      videos: [],
      videoPage: true,
      chatPage: false, 
      avatarUrl: "" 
    }
  }

  componentDidMount() {
    getProfileData()
      .then(data => {
        let avUrl = "/api/avatar?artist=" + data.Id

        this.setState({
          name: data.Name,
          username: data.Username,
          desc: data.Desc,
          followers: data.FollowerCount,
          likes: data.LikeCount,
          videos: data.VideoList,
          avatarUrl: avUrl 
        })
      })
      .catch(error => {
        console.error(error)
      })
  }

  tabChange = (event, value) => {
    switch(value) {
      case 0:
        this.setState({videoPage: true, chatPage: false})
        break
      case 1:
        this.setState({videoPage: false, chatPage: true})
        break
      default:
        this.setState({videoPage: true, chatPage: false})
        break
    }
  }

  edit = () => {
    document.getElementById("edit").click()
  }

  addVideo = () => {
    document.getElementById("newvideo").click()
  }

  getBody = () => {
    const videoCards = []
    if(this.state.videos !== null) {
      for(let i = 0; i < this.state.videos.length; i++) {
        const data = this.state.videos[i]
  
        videoCards.push(
          <VideoCard
            style={{margin: 40}}
            id={data.Id}
            artist={data.Artist.Id}
            desc={data.Desc}
            name={data.Title}
          />
        )
      }
    }
    else {
      videoCards.push(
        <Paper style={{padding: 10, margin: 20}}>
          <Typography>This user has no videos</Typography>
        </Paper>
      )
    }
    
    return videoCards
  }

  render() {
    const bodyStyle = {
      backgroundColor: "#e8e8e8",
      display: "flex",
      flexDirection: "column",
      justifyContent: "flex-start"
    }

    return (
      <div>
        <Header/>
        <div style={bodyStyle}>
          <Paper style={{padding: 10, width: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
            <div style={{display: "flex", flexDirection: "row"}}> 
              <IconButton onClick={this.edit}>
                <Settings/>
              </IconButton>
              <Avatar src={this.state.avatarUrl} style={{width: 100, height: 100, marginBotton: 10}}/>
              <IconButton onClick={this.addVideo}>
                <AddCircle/>
              </IconButton>
            </div> 
            <Typography>{this.state.name}</Typography> 
            <Typography>{this.state.username}</Typography> 
          </Paper>
        </div>
        <div style={{height: "-webkit-fill-available", backgroundColor: "#e8e8e8", display: "flex", flexWrap: "wrap", justifyContent: "center"}}>
          {this.getBody()}
        </div>
        <Link id="edit" to="/editprofile" style={{visibility: "collapse"}}/>
        <Link id="newvideo" to="/addvideo" style={{visibility: "collapse"}}/>
        <Link id="video" to="/profile" style={{visibility: "collapse"}}/>
        <Link id="chat" to="/chat" style={{visibility: "collapse"}}/>
      </div>
    )
  }
}

export default Profile;
