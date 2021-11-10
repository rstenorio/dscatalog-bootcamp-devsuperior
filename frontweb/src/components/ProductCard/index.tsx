import "./styles.css";
import Product from 'assets/images/computer.jpg';
import ProductPrice from "../ProductPrice";

const ProductCard = () => {
  return (
    <>
      <div className='base-card product-card'>
        <div className='card-top-container'>
            <img src={Product} alt='DeskTop'/>
        </div>
        <div className='card-bottom-container'>
            <h6>DeskTop HP Pavilion</h6>
            <ProductPrice />
        </div>
      </div>
    </>
  );
};

export default ProductCard;
