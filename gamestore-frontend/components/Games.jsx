import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { BsSearch, BsDatabaseFillAdd, BsDatabaseFillDash } from 'react-icons/bs';
import { FiPlus } from 'react-icons/fi';
import { MdDelete } from 'react-icons/md';
import PurchaseForm from '@/components/PurchaseForm'; // Ensure the correct import path

async function fetchGameData() {
  try {
    const response = await axios.get('http://localhost:8080/games');
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching data:', error);
    return [];
  }
}

function changeColor(quantity) {
  if (quantity < 5) {
    console.log('Changing color to text-red-500');
    return 'text-red-500';
  } else if (quantity < 10) {
    console.log('Changing color to text-yellow-500');
    return 'text-yellow-500';
  } else if (quantity < 20) {
    console.log('Changing color to text-green-500');
    return 'text-green-500';
  } else if (quantity < 50) {
    console.log('Changing color to text-blue-500');
    return 'text-blue-500';
  }
}

function postGame(event) {
  event.preventDefault();

  const form = event.target;
  const formData = new FormData(form);

  const formDataObject = {};
  formData.forEach((value, key) => {
    formDataObject[key] = value;
  });

  axios
    .post('http://localhost:8080/games', formDataObject, {
      headers: {
        'Content-Type': 'application/json',
      },
    })
    .then((response) => {
      if (response.status === 200) {
        console.log('Data sent successfully');
      } else {
        console.error('Error sending data');
      }
    })
    .catch((error) => {
      console.error('Network error:', error);
    });

  window.location.reload();
}

function deleteGameById(id) {
  axios
    .delete(`http://localhost:8080/games/${id}`)
    .then((response) => {
      if (response.status === 200) {
        console.log('Data sent successfully');
      } else {
        console.error('Error sending data');
      }
    })
    .catch((error) => {
      console.error('Network error:', error);
    });

  window.location.reload();
}

function Games({ showPurchaseForm, setShowPurchaseForm, onPurchase }) {
  const [games, setGames] = useState([]);
  const [search, setSearch] = useState('');
  const [searchBy, setSearchBy] = useState('title');
  const [isFiltered, setIsFiltered] = useState(false);
  const [showDatabaseWindow, setShowDatabaseWindow] = useState(false);
  const [title, setTitle] = useState('');
  const [rating, setRating] = useState('');
  const [description, setDescription] = useState('');
  const [studio, setStudio] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState(0);
  const [deleteMode, setDeleteMode] = useState(false);

  function filterGames(event) {
    isFiltered ? setIsFiltered(false) : setIsFiltered(true);

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
          'Content-Type': 'application/json',
        },
      })
      .then((response) => {
        console.log(response.data);
        setGames(response.data);
        setShowFilteredGames(true);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }

  async function fetchGameData() {
    try {
      const response = await axios.get('http://localhost:8080/games');
      console.log(response.data);
      setGames(response.data);
      return response.data;
    } catch (error) {
      console.error('Error fetching data:', error);
      return [];
    }
  }
  function addToPurchase(game) {
    console.log(game.game_id, game.title, game.price);
    // Call the parent component's onPurchase function
    onPurchase(game.game_id, game.title, game.price, game.quantity);
  }

  useEffect(() => {
    async function fetchData() {
      const data = await fetchGameData();
      setGames(data);
    }
    fetchData();
  }, []);

  return (
    <div>
      <h2 className='inline flex-nowrap text-lg xl:text-2xl font-bold text-white drop-shadow-md duration-100'> Games </h2>

      <BsDatabaseFillAdd className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)} />
      <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setDeleteMode(!deleteMode)} />

      <form className='flex gap-2 text-white' onSubmit={filterGames}>
        <div className='flex gap-2 pt-3 flex-nowrap'>
          <h1 className='text-white inline-block min-w-max'>Find By</h1>
          <select className='px-1 inline-block w-min h-min flex-1 text-black' name="searchBy" onChange={(e) => setSearchBy(e.target.value)}>
            <option value="title">Title</option>
            <option value="rating">Rating</option>
            <option value="studio">Studio</option>
          </select>
          :
          <input required className='px-1 h-min text-black' type="text" name="search" onChange={(e) => setSearch(e.target.value)} />
          {isFiltered ? <button className='text-red-400' onClick={fetchGameData}> X </button> : <button className='px-1' type="submit"><BsSearch /></button>}
        </div>
      </form>
      <div>
        {/* insertion card */}
        <ul className='flex-col '>
          {showDatabaseWindow &&
            <div className='my-4 hover:cursor-pointer hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg max-w-md '>
              <form className='flex flex-col gap-2 text-black' onSubmit={postGame}>
                <h1 className='text-2xl font-bold text-black'>Add Game</h1>
                <label className='text-black inline'>Title:
                <input required className='px-1 text-black bg-transparent border-b border-black inline' type="text" name="title" onChange={(e) => setTitle(e.target.value)} />
                </label>

                <label className='text-black'>Rating:
                <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="rating" onChange={(e) => setRating(e.target.value)} />
                </label>

                <label className='text-black'>Description:</label>
                <textarea required className='px-1 text-black bg-transparent border border-black resize-none w-4/5' name="description" onChange={(e) => setDescription(e.target.value)}></textarea>

                <label className='text-black'>Studio:
                <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="studio" onChange={(e) => setStudio(e.target.value)} />
                </label>

                <label className='text-black'>Price:
                <input required className='px-1 text-black bg-transparent border-b border-black' type="text" name="price" onChange={(e) => setPrice(e.target.value)} />

                </label>

                <label className='text-black'>Quantity:
                <input required className='px-1 text-black bg-transparent border-b border-black' type="number" name="quantity" onChange={(e) => setQuantity(Number(e.target.value))} />
                </label>
                <button className='rounded-lg bg-slate-800 text-white p-2 hover:translate-x-1 duration-100' type="submit">Submit</button>
              </form>
            </div>
          }

          {/* games from database */}
          {games.map((game) => (
            <div className="my-4 flex-col  hover:translate-x-[.1rem] duration-150 bg-slate-50 text-gray-600 p-2 rounded-lg " key={game.game_id}>
              <div className='flex justify-between'>
                <h1 className=''><span className='text-black text-lg'>Title: </span>
                  {game.title}
                </h1>

                {showPurchaseForm ? <FiPlus onClick={() => addToPurchase(game)} className='text-xl text-blue-600' /> : deleteMode && <button onClick={() => (deleteGameById(game.game_id))}><MdDelete className='text-red-600 text-xl hover:scale-[1.1] hover:text-red-300 duration-150' /></button>}
              </div>

              <h1><span className='text-black text-lg'>Rating: </span> {game.rating}</h1>
              <span><h1 className='text-black text-lg'> Description: </h1> <p className='max-w-xs px-1'>{game.description}</p></span>
              <h1><span className='text-black text-lg'>Studio:</span> {game.studio}</h1>
              <div>
                <ul className='flex'>
                  <li className='relative top-[.5rem] '><span className='text-green-500 text-lg '>Price:</span> {game.price}</li>
                  <li className={`relative ml-auto left-2 top-[.5rem] w-min text-right rounded-s-lg p-1 ${changeColor(game.quantity)}`}>{game.quantity}</li>
                </ul>
              </div>
            </div>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default Games;
