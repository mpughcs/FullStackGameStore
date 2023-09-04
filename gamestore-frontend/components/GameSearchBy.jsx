// GameSearchBy.js
import React, { useState } from 'react';
import { BsSearch } from 'react-icons/bs';
import axios from 'axios';
import Games from './Games'; // Import the Games component

export default function GameSearchBy() {
  const [search, setSearch] = useState('');
  const [searchBy, setSearchBy] = useState('title');
  const [games, setGames] = useState([]);
  const [consoles, setConsoles] = useState([]);
  const [shirts, setShirts] = useState([]);
  const [showFilteredGames, setShowFilteredGames] = useState(false); // State to control rendering filtered games

  function handleSubmit(event) {
    event.preventDefault();
    const form = event.target;
    const formData = new FormData(form);
    const formDataObject = {};
    formData.forEach((value, key) => {
      formDataObject[key] = value;
    });
    console.log(`http://localhost:8080/games/${searchBy}/${search}`);
    axios
      .get(`http://localhost:8080/games/${searchBy}/${search}`, formDataObject, {
        headers: {
          'Content-Type': 'application/json', // Set the content type to JSON
        },
      })
      .then((response) => {
        console.log(response.data);
        setGames(response.data); // Update the games state with filtered results
        setShowFilteredGames(true); // Set the state to render filtered games
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }

  return (
    <div>
      <form className='flex gap-2 text-white' onSubmit={handleSubmit}>
        <div className='flex gap-2 pt-3 flex-nowrap'>
          <h1 className='text-white inline-block min-w-max'>Find By</h1>
          <select className='px-1 inline-block w-min h-min flex-1 text-black' name="searchBy" onChange={(e) => setSearchBy(e.target.value)}>
            <option value="title">Title</option>
            <option value="rating">Rating</option>
            <option value="studio">Studio</option>
          </select>
          :
          <input required className='px-1 h-min text-black' type="text" name="search" onChange={(e) => setSearch(e.target.value)} />
          <button className='px-1' type="submit"><BsSearch /></button>
        </div>
      </form>
      
    </div>
  );
}
