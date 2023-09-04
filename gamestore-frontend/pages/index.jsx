import Image from 'next/image'
import { Inter } from 'next/font/google'
import { useState } from 'react'
import { BsFillMoonStarsFill, BsDatabaseFillAdd ,BsDatabaseFillDash, BsDatabaseFillUp} from "react-icons/bs";
import Consoles from '../components/Consoles'
import Games from '../components/Games'
import Shirts from '../components/Shirts'
import SearchBy from '../components/GameSearchBy'
// compile these into a single import 
import ConsoleSearchBy from '@/components/ConsoleSearchBy';

// import DatabaseForm from '@/components/DatabaseForm'; 
import GameSearchBy from '../components/GameSearchBy';

const inter = Inter({ subsets: ['latin'] });

export default function Home() {
  const [showEditConsole, setShowEditConsole] = useState(false);
  const [showEditGame, setShowEditGame] = useState(false);
  const [showEditShirt, setShowEditShirt] = useState(false);
  const [showDatabaseWindow, setShowDatabaseWindow] = useState(false);


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

  return (
    <main className={`min-h-screen  bg-gradient-radial from-blue-950 to-black bg-opacity-20 `}>
      <section className={`min-h-screen px-14 py-10 font-mulish  `}>
      <nav className={`flex justify-between`}>
          <h1 className='text-5xl font-bold  text-white drop-shadow-md'>Game Store</h1>
          <ul className={` flex `}>
            <span className="hover:translate-x-1 duration-100">
              <li className="rounded-se-xl bg-gradient-to-br from-slate-800 to-slate-200-100 text-slate-200  cursor-pointer p-2 lg:text-xl font-mulish">
                <a href="/">Code</a>
              </li>
            </span>
            <span className="hover:translate-x-1 duration-100">
              <li className="rounded-se-xl bg-gradient-to-br from-slate-800 to-slate-200-100 text-slate-200  cursor-pointer p-2 lg:text-xl font-mulish">
                <a href="/">About</a>
              </li>
            </span>
          </ul>
        </nav>
        <p className='max-w-4xl pt-3'>
          This is a full-stack web application that allows users to browse, edit and purchase games, consoles and shirts. The frontend is built with Next.js and Tailwind CSS. The backend is built with Java Spring Boot and MySQL.
        </p>

        <div className='px-10 pt-10'>
          <h2 className='text-4xl font-bold text-white drop-shadow-md'> Inventory </h2>
          <div className=''>
          </div>
          <div className= {` gap-4 flex ${showDatabaseWindow ? 'justify-start': 'justify-start'}`}>

            <span className={`duration-300 hover:translate-x-1 flex-1 ${showDatabaseWindow ? 'basis-1/5': 'flex-1'}`}>
              {/* <h2 className='inline flex-nowrap text-lg xl:text-2xl font-bold text-white drop-shadow-md duration-100'> Games </h2> */}
              {/* <BsDatabaseFillAdd className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/> */}
              {/* <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/> */}
              {/* <GameSearchBy /> */}
              <Games />
            </span>
            <span className={`duration-300 hover:translate-x-1 ${showDatabaseWindow ? 'display: none': 'flex-1'}`}>
              {/* <h2 className='inline flex-nowrap text-lg xl:text-2xl font-bold text-white drop-shadow-md duration-100'> Consoles </h2>
              <BsDatabaseFillAdd className={`text-lg  mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/>
              <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/>
              <ConsoleSearchBy /> */}
              <Consoles />
            </span>

            <span className={`duration-300 hover:translate-x-1 ${showDatabaseWindow ? 'basis-1/5 ': 'flex-1'}`}>
              <h2 className='inline text-lg xl:text-2xl font-bold text-white drop-shadow-md pt-10 duration-100'> Shirts </h2>
              <BsDatabaseFillAdd className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/>
              <BsDatabaseFillDash className={`text-lg mx-2 inline relative hover:translate-x-[.1rem] hover:cursor-pointer duration-200`} onClick={() => setShowDatabaseWindow(!showDatabaseWindow)}/>
              <GameSearchBy />

              <Shirts />
            </span>

            
          </div>
        </div>
      </section>
    </main>
  );
}
