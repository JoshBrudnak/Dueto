import React, { Component } from 'react';
import Header from '../component/Header.js'
import GenreCard from '../component/GenreCard.js'
import {getGenres} from '../utils/fetchData.js'

class Discover extends Component {
  constructor() {
    super()
    
    this.state = {
      genres: [],
      selGenre: undefined
    }
  }

  componentDidMount() {
    getGenres()
      .then(data => {
        this.setState({genres: data.GenreList})
      })
      .catch(error => {
        console.error(error)
      })
  }

  getGenres = () => {
    const gCards = []

    for(let i = 0; i < this.state.genres.length; i++) {
      let genre = this.state.genres[i]

      gCards.push(
        <GenreCard style={{margin: 20}} name={genre.Name} desc={genre.Description}/>
      )
    }
    
    return gCards
  }

  render() {
    return (
      <div>
        <Header/>
        <div style={{margin: 20}}>
          {this.getGenres()}
        </div>
      </div>
    );
  }
}

export default Discover;
