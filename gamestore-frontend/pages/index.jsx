import React, { useState } from 'react';
import axios from 'axios';
import { BsSearch, BsDatabaseFillAdd, BsDatabaseFillDash } from 'react-icons/bs';
import { FiPlus } from 'react-icons/fi';
import { MdDelete } from 'react-icons/md';
import PurchaseForm from '@/components/PurchaseForm';
import Games from '../components/Games';
import Consoles from '../components/Consoles';
import Shirts from '../components/Shirts';
import { AiOutlineShoppingCart } from 'react-icons/ai';





export default function Home() {
  const [showEditConsole, setShowEditConsole] = useState(false);
  const[invoiceTable, setInvoiceTable] = useState('');
  const [state, setState] = useState(''); // State for the search query
  const [customerName, setCustomerName] = useState('');
  const [street, setStreet] = useState('');
  const [city, setCity] = useState('');
  const [purchaseQuantity, setPurchaseQuantity] = useState(0);
  const [showEditGame, setShowEditGame] = useState(false);
  const [showEditShirt, setShowEditShirt] = useState(false);
  const [showDatabaseWindow, setShowDatabaseWindow] = useState(false);
  const [showPurchaseForm, setShowPurchaseForm] = useState(false);
 
  // Function to toggle the editConsole state
  function editConsole() {
    setShowEditConsole(!showEditConsole);
  }

  // Function to toggle the editGame state
  function editGame() {
    setShowEditGame(!showEditGame);
  }

  // Function to toggle the editShirt state
  function editShirt() {
    setShowEditShirt(!showEditShirt);
  }

  const handleGamePurchase = (gameId, title, price, quantity ) => {
    // Here, you can access the game ID and perform actions, e.g., add to a shopping cart
    console.log(`Purchased game with ID: ${gameId} and title: ${title} for $${price} with quantity: ${quantity}`);
    // You can perform further actions here, such as updating a shopping cart state
  };

  return (
    <main className={`min-h-screen  bg-gradient-radial from-blue-950 to-black bg-opacity-20 `}>
      <section className={`min-h-screen px-14 py-10 font-mulish  `}>
        <nav className={`flex justify-between`}>
          <h1 className='text-5xl font-bold  text-white drop-shadow-md'>Game Store</h1>
          <ul className={` flex `}>
            <span className="hover:translate-x-1 duration-100 ">
              <li className="rounded-se-xl flex bg-gradient-to-br from-slate-800 to-slate-200-100 text-slate-200  cursor-pointer p-2 lg:text-xl font-mulish">
                {/* q:toggle purchase form */}
                <AiOutlineShoppingCart onClick={() => setShowPurchaseForm(!showPurchaseForm)} className="text-3xl m-[.35rem]" />
              </li>
            </span>
          </ul>
        </nav>
        {showPurchaseForm && <PurchaseForm />}
        <p className='max-w-4xl pt-3'>
          This is a full-stack web application that allows users to browse, edit and purchase games, consoles, and shirts. The frontend is built with Next.js and Tailwind CSS. The backend is built with Java Spring Boot and MySQL.
        </p>
        <div className='sm:px-10 pt-10'>
          <h2 className='text-4xl font-bold text-white drop-shadow-md'> Inventory </h2>
          <div className=''>
          </div>
          <div className={` gap-4 flex flex-col xl:flex-row`}>
            <span className={`duration-300 hover:translate-x-1  `}>
            <Games
              showPurchaseForm={showPurchaseForm}
              setShowPurchaseForm={setShowPurchaseForm}
              onPurchase={handleGamePurchase} // Pass the onPurchase function as a prop
            />
            </span>
            <span className={`duration-300 hover:translate-x-1 `}>
              <Consoles />
            </span>

            <span className={`duration-300 hover:translate-x-1 `}>

              <Shirts />
            </span>
          </div>
        </div>
      </section>
    </main>
  );
}
