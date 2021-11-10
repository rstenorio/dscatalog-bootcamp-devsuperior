import { ReactComponent as MainImage } from "assets/images/Desenho.svg";
import ButtonIcon from "../../components/ButtonIcon";
import "./styles.css";
import { Link } from "react-router-dom";

const Home = () => {
  return (
    <div className="home-container">
      <div className="base-card home-card ">
        <div className="home-content-container">
          <div>
            <h1>Conheca o melhor catalago de produtos</h1>
            <p>
              Ajudaremos voce a encontrar os melhores produtos disponbiveis no
              mercado.{" "}
            </p>
          </div>
          <Link to='/products'>
            <ButtonIcon />
          </Link>
        </div>
        <div className="home-image-container">
          <MainImage />
          {/* <img src={Pizza} alt="Pizza" /> */}
        </div>
      </div>
    </div>
  );
};

export default Home;
