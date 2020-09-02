import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ConsultorService } from 'app/entities/consultor/consultor.service';
import { IConsultor, Consultor } from 'app/shared/model/consultor.model';

describe('Service Tests', () => {
  describe('Consultor Service', () => {
    let injector: TestBed;
    let service: ConsultorService;
    let httpMock: HttpTestingController;
    let elemDefault: IConsultor;
    let expectedResult: IConsultor | IConsultor[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ConsultorService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Consultor(0, 'AAAAAAA', 'AAAAAAA', currentDate, 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dataContratacao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Consultor', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataContratacao: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.create(new Consultor()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Consultor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cnpj: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            telefone: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Consultor', () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            cnpj: 'BBBBBB',
            dataContratacao: currentDate.format(DATE_FORMAT),
            email: 'BBBBBB',
            telefone: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataContratacao: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Consultor', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
