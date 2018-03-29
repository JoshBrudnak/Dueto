import React, { Component } from 'react'
import VideoCard from '../component/VideoCard.js'
import Header from '../component/Header.js'
import {getGenreVideos} from '../utils/fetchData.js'

class Genre extends Component {
  constructor() {
    super() 

    this.state = {
      videos: []
    }
  }

  componentDidMount() {
    const value = this.props.match.params.name

    getGenreVideos(value)
      .then(data => {
        console.log(data)
        this.setState({videos: data.VideoCards}) 
      })
      .catch(error => {
        console.error(error)
      })
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
    console.log("render")
    return (
      <div>
        <Header/>
        {this.getVideoCards()}
      </div>
    )
  }
}

export default Genre;
