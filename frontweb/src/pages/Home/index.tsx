import { ReactComponent as MainImage } from "assets/images/Desenho.svg";
// import Pizza from '../../assets/images/pizzabg.png'
import Navbar from "../../components/Navbar";
import ButtonIcon from "../../components/ButtonIcon";
import "./styles.css";

const Home = () => {
  return (
    <>
      <Navbar />
      <div className="home-container">
        <div className="home-card ">
          <div className="home-content-container">
            <div>
              <h1>Conheca o melhor catalago de produtos</h1>
              <p>Ajudaremos voce a encontrar os melhores produtos disponbiveis no mercado. </p>
            </div>
            <ButtonIcon />
          </div>
          <div className="home-image-container">
            <MainImage />
            {/* <img src={Pizza} alt="Pizza" /> */}
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;
