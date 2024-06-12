import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trajet.reducer';

export const TrajetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const trajetEntity = useAppSelector(state => state.trajet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trajetDetailsHeading">Trajet</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{trajetEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{trajetEntity.date ? <TextFormat value={trajetEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="heuredepart">Heuredepart</span>
          </dt>
          <dd>{trajetEntity.heuredepart ? <TextFormat value={trajetEntity.heuredepart} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="heurearrivee">Heurearrivee</span>
          </dt>
          <dd>
            {trajetEntity.heurearrivee ? <TextFormat value={trajetEntity.heurearrivee} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lieudepart">Lieudepart</span>
          </dt>
          <dd>{trajetEntity.lieudepart}</dd>
          <dt>
            <span id="lieuarrivee">Lieuarrivee</span>
          </dt>
          <dd>{trajetEntity.lieuarrivee}</dd>
          <dt>
            <span id="nombreplace">Nombreplace</span>
          </dt>
          <dd>{trajetEntity.nombreplace}</dd>
          <dt>
            <span id="prixplace">Prixplace</span>
          </dt>
          <dd>{trajetEntity.prixplace}</dd>
          <dt>
            <span id="marqueVehicule">Marque Vehicule</span>
          </dt>
          <dd>{trajetEntity.marqueVehicule}</dd>
        </dl>
        <Button tag={Link} to="/trajet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Retour</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trajet/${trajetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editer</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrajetDetail;
