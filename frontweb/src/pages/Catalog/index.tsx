import ProductCard from "../../components/ProductCard";
import "./styles.css";

const Catalog = () => {
  return (
    <>
      <div className="container my-4">
        <div><h5>Catálago de Produtos</h5></div>
        <div className="row">
        <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

          <div className="col-sm-6 col-lg-4 col-xl-3">
            <ProductCard />
          </div>

        </div>
      </div>
    </>
  );
};

export default Catalog;
