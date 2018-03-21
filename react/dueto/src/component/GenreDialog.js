import React, { Component } from 'react'
import {Typography, Button, Radio, FormControl, FormControlLabel, RadioGroup} from 'material-ui'
import Dialog, {DialogTitle, DialogContent} from 'material-ui/Dialog'
import {getGenres} from "../utils/fetchData.js"

class GenreDialog extends Component {
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

  getGenreList = () => {
    const genreList = []
    
    for(let i = 0; i < this.state.genres.length; i++) {
      const genre = this.state.genres[i]

      genreList.push(
        <FormControlLabel value={genre.Name} control={<Radio/>} label={genre.Name}/>
      )
    }

    return genreList
  }

  genreChange = (event) => {
    this.setState({selGenre: event.target.value})
  }

  save = () => {
    this.props.genre(this.state.selGenre)
  }

  cancel = () => {
    this.props.close()
  }

  render() {
    return (
      <Dialog open={this.props.open} onClose={this.props.close}>
        <DialogTitle>Genres</DialogTitle>
        <DialogContent>
          <FormControl>
            <RadioGroup value={this.state.selGenre} onChange={this.genreChange}>
              {this.getGenreList()}
            </RadioGroup>
          </FormControl>
          <Button size="small" color="primary" onClick={this.save}>Ok</Button>
          <Button size="small" color="primary" onClick={this.cancel}>Cancel</Button>
        </DialogContent> 
      </Dialog>
    )
  }
}

export default GenreDialog
