import React, { Component } from 'react'
import { Paper, Tab, Tabs, IconButton, TextField } from 'material-ui'
import AccountCircle from 'material-ui-icons/AccountCircle'
import {Link} from 'react-router-dom'
import Header from '../component/Header.js'
import VideoCard from '../component/VideoCard.js'
import {getVideoCards, getHomeData} from '../utils/fetchData.js'

class Home extends Component {
  constructor() {
    super() 

    this.state = {
      tab: undefined,
      videos: []
    }
  }

  componentDidMount() {
    getHomeData()
      .then(data => {
         this.setState({videos: data.VideoCards}) 
      })
      .catch(error => {})
  }

  getVideoCards = () => {
    const cards = [] 
    
    for(let i = 0; i < this.state.videos.length; i++) {
      const data = this.state.videos[i]
      cards.push(
        <VideoCard
          artist={data.Artist.Id}
          desc={data.Desc}
          genre={data.Genre}
          name={data.Title}
          likes={data.Likes}
          views={data.Views}
        />
      )
    }

    return cards
  }

  render() {
    const {tab} = this.state

    return (
      <div>
        <Header/>
        {this.getVideoCards()}
      </div>
    )
  }
}

export default Home;
