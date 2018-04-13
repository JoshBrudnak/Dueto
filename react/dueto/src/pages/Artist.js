import React, { Component } from 'react'
import {Avatar, Typography, Paper, Tabs, Tab} from 'material-ui'
import Header from '../component/Header.js'
import VideoCard from '../component/VideoCard.js'
import MessageView from '../component/MessageView.js'
import {getArtistData} from '../utils/fetchData.js'

class Artist extends Component {
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
      avatarUrl: "/api/avatar?artist=0" 
    }
  }

  componentDidMount() {
    const id = this.props.match.params.name

    getArtistData(id)
      .then(data => {
        const avUrl = "/api/avatar?artist=" + id
        
        this.setState({
          id: id,
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
    document.getElementById("addvideo").click()
  }

  getBody = () => {
    if(this.state.videoPage && this.state.videos !== null) {
      const videoCards = []
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
      
      return videoCards
    }
    else if(this.state.chatPage) {
      return (
        <MessageView style={{height: "fit-content", maxHeight: 800}} artist={this.state.id}/>
      )
    }
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{backgroundColor: "#e8e8e8", display: "flex", flexDirection: "column", justifyContent: "flex-start"}}>
          <Paper style={{padding: 10, width: "-webkit-fill-available", display: "flex", flexDirection: "column", alignItems: "center"}}>
            <Avatar src={this.state.avatarUrl} style={{width: 100, height: 100, marginBotton: 10}}/>
            <Typography>{this.state.name}</Typography> 
            <Typography>{this.state.username}</Typography> 
          </Paper>
          <Paper style={{display: "grid", alignItems: "center"}}>
            <div style={{gridColumn: 2}}>
              <Tabs onChange={this.tabChange}>
                <Tab style={{width: 100}} label="Video" />
                <Tab style={{width: 100}} label="Chat" />
              </Tabs>
            </div>
          </Paper>
        </div>
        <div style={{backgroundColor: "#e8e8e8", height: "-webkit-fill-available", display: "flex", flexWrap: "wrap", justifyContent: "center"}}>
          {this.getBody()}
        </div>
      </div>
    )
  }
}

export default Artist;
