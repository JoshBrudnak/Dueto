import React, { Component } from 'react'
import Card, {CardMedia} from 'material-ui/Card'
import {Link} from 'react-router-dom'

class GenreCard extends Component {
  render() {
    let genreImUrl = "/api/genreimage?name=" + this.props.name
    let genreUrl = "/genre/" + this.props.name

    return (
        <Card>
          <Link to={genreUrl}>
            <CardMedia>
              <img alt="genre icon" src={genreImUrl} style={{width: 400, height: 400}}/>
            </CardMedia>
          </Link>
        </Card>
    )
  }
}

export default GenreCard
